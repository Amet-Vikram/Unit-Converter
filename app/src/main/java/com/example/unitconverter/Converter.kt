package com.example.unitconverter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*

class Converter : Fragment(R.layout.fragment_converter) {

    private lateinit var userValueView : EditText
    lateinit var fragmentContext : Context
    var userValue : Double = 0.0
    var factor : Double = 0.0
    var mainFactor : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = requireContext().applicationContext
    }

    override fun onStart() {
        super.onStart()

        val quantitySpinner : Spinner = requireView().findViewById(R.id.quantity)
        val unitSpinner : Spinner = requireView().findViewById(R.id.unit)
        val resUnitSpinner : Spinner = requireView().findViewById(R.id.result_unit)
        userValueView = requireView().findViewById(R.id.editUserValue)
        var userResultView : TextView = requireView().findViewById(R.id.userResult)
        lateinit var unitAdapter : ArrayAdapter<CharSequence>
        val quantityFactorList : ArrayList<Double> = arrayListOf()
        val convertButton : Button = requireView().findViewById(R.id.btnConvert)

        userValueView.addTextChangedListener(textWatcher)

        //Array Adapter for quantities
        ArrayAdapter.createFromResource(
            fragmentContext,
            R.array.quantity,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            quantitySpinner.adapter = adapter
        }

        quantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0){
                    unitAdapter = ArrayAdapter.createFromResource(
                        fragmentContext,
                        R.array.unit_len,
                        android.R.layout.simple_spinner_item
                    )
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    unitSpinner.adapter = unitAdapter
                    resUnitSpinner.adapter = unitAdapter
                    val lenArray : Array<UnitCategory.Length> = UnitCategory.Length.values()
                    for(units in lenArray){
                        quantityFactorList.add(units.factor)
                    }
                }
                if(position == 1){
                    unitAdapter = ArrayAdapter.createFromResource(
                        fragmentContext,
                        R.array.unit_vol,
                        android.R.layout.simple_spinner_item
                    )
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    unitSpinner.adapter = unitAdapter
                    resUnitSpinner.adapter = unitAdapter
                    val volArray : Array<UnitCategory.Volume> = UnitCategory.Volume.values()
                    for(units in volArray){
                        quantityFactorList.add(units.factor)
                    }
                }
                if(position == 2){
                    unitAdapter = ArrayAdapter.createFromResource(
                        fragmentContext,
                        R.array.unit_wt,
                        android.R.layout.simple_spinner_item
                    )
                    unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    unitSpinner.adapter = unitAdapter
                    resUnitSpinner.adapter = unitAdapter
                    val wtArray : Array<UnitCategory.Weight> = UnitCategory.Weight.values()
                    for(units in wtArray){
                        quantityFactorList.add(units.factor)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //Assigning the factor based on spinner choice
        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainFactor = quantityFactorList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        resUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                factor = quantityFactorList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        convertButton.setOnClickListener{
            if(mainFactor < factor){
                userValue *= mainFactor
                userResultView.text = convert(userValue, factor).toString()
            }else if(mainFactor > factor){
                userValue /= mainFactor
                userResultView.text = convert(userValue, factor).toString()
            }
            else{
                userResultView.text = userValue.toString()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(et: Editable?) {
            if(et.toString().isEmpty()){
                Toast.makeText(fragmentContext, "Can't be left empty.", Toast.LENGTH_SHORT).show()
            }else{
                userValue = et.toString().toDouble()
            }
        }
    }

    private fun convert(value: Double, factor: Double) : Double{
        return value * factor
    }
}
