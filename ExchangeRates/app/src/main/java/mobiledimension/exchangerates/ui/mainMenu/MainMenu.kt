package mobiledimension.exchangerates.ui.mainMenu

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import android.widget.*
import mobiledimension.exchangerates.AdapterModelData
import mobiledimension.exchangerates.MyApplication.Companion.appComponent
import mobiledimension.exchangerates.R
import mobiledimension.exchangerates.utils.NetworkChangeReceiver
import mobiledimension.exchangerates.di.DaggerActivityComponent
import mobiledimension.exchangerates.presenter.mainMenu.MainPresenter
import mobiledimension.exchangerates.ui.dataPickerFragment.DatePickerFragment
import java.util.*
import javax.inject.Inject


class MainMenu : AppCompatActivity(), MainView, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, DatePickerFragment.OnDateSetListener {

    @Inject
    lateinit var networkChangeReceiver: NetworkChangeReceiver
    @Inject
    lateinit var mainPresenter: MainPresenter<MainView>
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var adapterModelData: AdapterModelData
    private lateinit var spinnerOfCurrencies: Spinner
    private val datePickerFragment = DatePickerFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //постоянно портретная ориентация
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)  //приложение на полный экран

        val activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
        activityComponent.inject(this)



        //region findViewById
        val currentDate = findViewById<View>(R.id.currentDateTextView) as TextView
        spinnerOfCurrencies = findViewById<View>(R.id.spinnerCurrency) as Spinner
        val listView = findViewById<View>(R.id.ListView) as ListView
        val sortRadioGroup = findViewById<View>(R.id.RadioGroup) as RadioGroup
        //endregion

        //Предварительная установка текущей даты.
        val date = DateFormat.format("yyyy-MM-dd", Date()).toString()
        currentDate.text = date
        mainPresenter.setCurrentDate(date)


        adapterModelData = AdapterModelData(this, R.layout.rates, mainPresenter.rates) //Адаптер списка с курсом валют
        listView.adapter = adapterModelData

        spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_item, mainPresenter.currencies) //Адаптер для спиннера
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOfCurrencies.adapter = spinnerAdapter


        spinnerOfCurrencies.onItemSelectedListener = this
        sortRadioGroup.setOnCheckedChangeListener(this)
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        mainPresenter.attachView(this)
    }


    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
        mainPresenter.detachView()
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mainPresenter.currencyChanged(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    override fun onDateSet(formattedDate: String) {
        mainPresenter.onDatePicked(formattedDate)
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        mainPresenter.onChangedSortType(checkedId)
    }


    fun onClickDate(view: View) {
        datePickerFragment.show(supportFragmentManager, "DataPicker")
    }

    override fun refreshSpinner() {
        spinnerAdapter.notifyDataSetChanged()
    }

    override fun spinnerSetSelection(position: Int) {
        spinnerOfCurrencies.setSelection(position)
    }

    override fun refreshAdapterModelDate() {
        adapterModelData.refresh()
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext,
                message, Toast.LENGTH_SHORT).show()
    }


}