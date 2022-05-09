package com.example.unitconverter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*

class AddUnits : Fragment(R.layout.fragment_add_units) {

    lateinit var fragmentContext : Context
    private lateinit var userValue1View : EditText
    private lateinit var userValue2View : EditText
    var userValue1 : Double = 0.0
    var userValue2 : Double = 0.0
    var resFactor : Double = 0.0
    var mainFactor1 : Double = 0.0
    var mainFactor2 : Double = 0.0
    private var val1 : Double  = 0.0
    private var val2 : Double  = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = requireContext().applicationContext
    }


    override fun onStart() {
        super.onStart()
        val quantitySpinner : Spinner = requireView().findViewById(R.id.spQuantity)
        val unitSpinner1 : Spinner = requireView().findViewById(R.id.spFirstUnit)
        val unitSpinner2 : Spinner = requireView().findViewById(R.id.spSecUnit)
        val resUnitSpinner : Spinner = requireView().findViewById(R.id.spResUnit)
        userValue1View = requireView().findViewById(R.id.etFirstValue)
        userValue2View = requireView().findViewById(R.id.etSecValue)
        var userResultView : TextView = requireView().findViewById(R.id.tvAddRes)
        lateinit var unitAdapter : ArrayAdapter<CharSequence>
        val quantityFactorList : ArrayList<Double> = arrayListOf()
        val convertButton : Button = requireView().findViewById(R.id.btnAdd)

        userValue1View.addTextChangedListener(textWatcher)
        userValue2View.addTextChangedListener(textWatcher)

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
                    unitSpinner1.adapter = unitAdapter
                    unitSpinner2.adapter = unitAdapter
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
                    unitSpinner1.adapter = unitAdapter
                    unitSpinner2.adapter = unitAdapter
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
                    unitSpinner1.adapter = unitAdapter
                    unitSpinner2.adapter = unitAdapter
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
        unitSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainFactor1 = quantityFactorList[position]
            }

            override fun onNothingSelected(av: AdapterView<*>?) {
                mainFactor1 = quantityFactorList[1]
            }
        }

        unitSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainFactor2 = quantityFactorList[position]

            }

            override fun onNothingSelected(av: AdapterView<*>?) {
                mainFactor2 = quantityFactorList[1]
            }
        }


        resUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                resFactor = quantityFactorList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                resFactor = quantityFactorList[1]
            }
        }

        convertButton.setOnClickListener{
            val1 = convertToMain(userValue1, mainFactor1)
            val2 = convertToMain(userValue2, mainFactor2)
            val result = (val1 + val2) * resFactor

            userResultView.text = result.toString()
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(et: Editable?) {
            when{
                et === userValue1View.editableText -> {
                    if(et.toString().isEmpty()){
                        Toast.makeText(fragmentContext, "Can't be left empty.", Toast.LENGTH_SHORT).show()
                    }else{
                        userValue1 = et.toString().toDouble()
                    }
                }
                et == userValue2View.editableText -> {
                    if(et.toString().isEmpty()){
                        Toast.makeText(fragmentContext, "Can't be left empty.", Toast.LENGTH_SHORT).show()
                    }else{
                        userValue2 = et.toString().toDouble()
                    }
                }
            }
        }
    }

    private fun convertToMain(value: Double, factor : Double) : Double {
        if(factor < resFactor){
            return value * factor
        }else if(factor > resFactor){
            return value / factor
        }
        else{
            return value
        }
    }
}