form taxOfficeExample {
  "Did you sell a house in 2010?"
    hasSoldHouse: boolean
  "Did you buy a house in 2010?"
    hasBoughtHouse: boolean
  "Did you enter a loan?"
    hasMaintLoan: boolean

  if (5 + 10) {
    "What was the selling price?"
      sellingPrice: money
    "Private debts for the sold house:"
      privateDebt: money
     if (true || true) {
          "What was the selling price?"
            sellingPrice: money
          "Private debts for the sold house:"
            privateDebt: money
        }
  }

}