package myapp

import myapp.ExampleData.Origin._
import myapp.ExampleData.Role._


object ExampleData {

  sealed trait Origin

  object Origin {

    case object EARTH extends Origin

    case object MARS extends Origin

    case object BELT extends Origin

  }

  sealed trait Role

  object Role {

    case class Captain(shipName: String) extends Role

    case class Pilot(shipName: String) extends Role

    case class Engineer(shipName: String) extends Role

    case class Mechanic(shipName: String) extends Role

  }

  def friendsConnections: CharactersFriendsArgs => Connection[Character] =
    args => ???

  case class CharactersFriendsArgs(limit: Int, before: Cursor, after: Cursor)

  type Cursor = String

  sealed trait Connection[T] {
    def pageInfo: PageInfo

    def edges: Edge[T]
  }

  case class Edge[T](node: T, cursor: Cursor)

  case class PageInfo(hasNextPage: Boolean)

  case class Character(name: String, nicknames: List[String], origin: Origin, role: Option[Role], friendsConnections: CharactersFriendsArgs => Connection[Character] = friendsConnections)

  case class CharactersArgs(origin: Option[Origin])

  case class CharacterArgs(name: String)

  val sampleCharacters = List(
    Character("James Holden", List("Jim", "Hoss"), EARTH, Some(Captain("Rocinante"))),
    Character("Naomi Nagata", Nil, BELT, Some(Engineer("Rocinante"))),
    Character("Amos Burton", Nil, EARTH, Some(Mechanic("Rocinante"))),
    Character("Alex Kamal", Nil, MARS, Some(Pilot("Rocinante"))),
    Character("Chrisjen Avasarala", Nil, EARTH, None),
    Character("Josephus Miller", List("Joe"), BELT, None),
    Character("Roberta Draper", List("Bobbie", "Gunny"), MARS, None)
  )
}
