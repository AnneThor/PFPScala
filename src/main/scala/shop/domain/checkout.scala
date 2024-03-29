package shop.domain

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.collection.Size
import eu.timepit.refined.string.{MatchesRegex, ValidInt}
import io.estatico.newtype.macros.newtype

object checkout {

  type Rgx = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"
//  type Rgx = W.`"^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"`.T
  type CardNamePred = String Refined MatchesRegex[Rgx]
  type CardNumberPred = Long Refined Size[16]
  type CardExpirationPred = String Refined (Size[4] And ValidInt)
  type CardCCVPred = Int Refined Size[3]

  @newtype case class CardName(value: CardNamePred)
  @newtype case class CardNumber(value: CardNumberPred)
  @newtype case class CardExpiration(value: CardExpirationPred)
  @newtype case class CardCCV(value: CardCCVPred)

  case class Card(
      name: CardName,
      number: CardNumber,
      expiration: CardExpiration,
      ccv: CardCCV
  )

}
