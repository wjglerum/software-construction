form taxOfficeExample {
  "Did you sell a house in 2010?"
    hasSoldHouse: boolean
  "Did you buy a house in 2010?"
    hasBoughtHouse: boolean
  "Did you enter a loan?"
    hasMaintLoan: boolean
  "Did you enter a loan?"
    hasMaintLoan2: boolean
"Did you buy a house in 2010?"
    hasMaintLoan2: boolean
    "Did you enter a loan?"
        hasSoldHouse: boolean


  if (hasSoldHouse + hasMaintLoan) {
    "What was the selling price?"
      sellingPrice: money
    if (privateDebt) {

    }
    "Private debts for the sold house:"
      privateDebt: money
    "Value residue:"
      valueResidue: money =
        (8 - 2 + 9 - privateDebt + joep)
  }

}