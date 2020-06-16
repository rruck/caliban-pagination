package myapp

import caliban.{GraphQL, RootResolver}

import scala.language.postfixOps
import myapp.ExampleData._
import myapp.ExampleService.ExampleService
import caliban.GraphQL.graphQL
import caliban.schema.Annotations.{GQLDeprecated, GQLDescription}
import caliban.schema.GenericSchema
import caliban.wrappers.ApolloTracing.apolloTracing
import caliban.wrappers.Wrappers.{maxDepth, maxFields, printSlowQueries, timeout}
import zio.URIO
import zio.clock.Clock
import zio.console.Console
import zio.duration._
import zio.stream.ZStream

object ExampleApi extends GenericSchema[ExampleService] {

  case class Queries(
                      @GQLDescription("Return all characters from a given origin")
                      characters: CharactersArgs => URIO[ExampleService, List[Character]],
                      @GQLDeprecated("Use `characters`")
                      character: CharacterArgs => URIO[ExampleService, Option[Character]]
                    )
  case class Mutations(deleteCharacter: CharacterArgs => URIO[ExampleService, Boolean])
  case class Subscriptions(characterDeleted: ZStream[ExampleService, Nothing, String])

  implicit val roleSchema           = gen[Role]
  implicit val characterSchema      = gen[Character]
  implicit val characterArgsSchema  = gen[CharacterArgs]
  implicit val charactersArgsSchema = gen[CharactersArgs]
  implicit val charactersFriendsArgsSchema = gen[CharactersFriendsArgs]
  implicit val charactersConnectionSchema = gen[Connection[Character]]
  implicit val characterFriendsEdge = gen[Edge[Character]]

  val api: GraphQL[Console with Clock with ExampleService] =
    graphQL(
      RootResolver(
        Queries(
          (args: CharactersArgs) => ExampleService.getCharacters(args.origin),
          (args: CharacterArgs) => ExampleService.findCharacter(args.name)
        )
      )
    ) @@
      maxFields(200) @@               // query analyzer that limit query fields
      maxDepth(30) @@                 // query analyzer that limit query depth
      timeout(3 seconds) @@           // wrapper that fails slow queries
      printSlowQueries(500 millis) @@ // wrapper that logs slow queries
      apolloTracing // wrapper for https://github.com/apollographql/apollo-tracing

}
