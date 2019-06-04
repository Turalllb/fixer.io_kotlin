package mobiledimension.exchangerates.ui.dataPickerFragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.Calendar

import mobiledimension.exchangerates.R


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {


    private var isFirstCall: Boolean? = true // Используется как костыль.   при нажатии на DataPicker он вызывается дважды. Исправлено в андроид 5.0
    private var currentDate: TextView? = null
    private var onDateSetListener: OnDateSetListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)


        val calendarMin = Calendar.getInstance() // Дата для установки нижнего порога в DataPiker
        calendarMin.set(1999, 0, 1) // http://fixer.io/ хранит курсы валют начиная с 1999 года . Отсчет месяцев идет с нуля.

        //region получаем дату из TextView и парсим в DataPiker, чтобы при открытии диалога, он был наведен на установленную дату
        currentDate = activity!!.findViewById(R.id.currentDateTextView)
        val date = currentDate!!.text.toString()
        if (date.isNotEmpty()) {
            val arrayDate = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            year = Integer.parseInt(arrayDate[0])
            month = Integer.parseInt(arrayDate[1]) - 1
            day = Integer.parseInt(arrayDate[2])
        }
        //endregion


        val datePickerDialog = DatePickerDialog(activity!!, this, year, month, day)
        val dp = datePickerDialog.datePicker
        dp.minDate = calendarMin.timeInMillis // ставим нижний диапазон в DatePiker
        dp.maxDate = System.currentTimeMillis() + 5000 // верхний диапазон   в DatePiker устанавливаем сегодняшним днем
        //к нему прибавлены 5 секунд, потому что на андароид 5.1.1 верхним пределом ставится не текущий день, а точное текущее время вплоть до миллисекунд.
        // Поэтому это 5 секунд времени, чтобы успеть вернуться к выбору текущей даты.


        return datePickerDialog
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onDateSetListener = context as OnDateSetListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must be implement OnDateSetListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        isFirstCall = true   // возвращаем исходное значение
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        if (isFirstCall!!) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate = sdf.format(calendar.time)
            currentDate!!.text = formattedDate
            onDateSetListener!!.onDateSet(formattedDate)

            isFirstCall = false
        }
    }

    interface OnDateSetListener {
        fun onDateSet(formattedDate: String)
    }


}
