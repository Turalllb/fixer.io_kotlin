package mobiledimension.exchangerates.data.db

object ExchangeRatesTable {

    const val TABLE = " ExchangeRatesTable"

    val CREATE_SCRIPT = ("create table ExchangeRatesTable ("
            + "date int,"
            + "currency text,"
            + "json text,"
            + "PRIMARY KEY(date, currency)"
            + ");")

    internal object COLUMN {
        const val DATE = "date"
        const val CURRENCY = "currency"
        const val JSON = "json"

    }

}
