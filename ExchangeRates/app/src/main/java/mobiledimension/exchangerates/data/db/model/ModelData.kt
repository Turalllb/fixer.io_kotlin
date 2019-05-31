package mobiledimension.exchangerates.data.db.model


import java.util.Comparator

/**
 * Created by Турал on 30.11.2017.
 */
/*Данный класс - модель данных, хранящий название валюты и ее курс, с методами их сравнения между собой*/
class ModelData internal constructor(val name: String, val value: Double) {
    companion object {

        val COMPARATOR_NAME: Comparator<ModelData> = Comparator { o1, o2 -> o1.name.compareTo(o2.name) }
        val COMPARATOR_VALUE_DESCENDING: Comparator<ModelData> = Comparator { o1, o2 -> o1.value.compareTo(o2.value) }
        val COMPARATOR_VALUE_ASCENDING: Comparator<ModelData> = Comparator { o1, o2 -> o2.value.compareTo(o1.value) }
    }
}
