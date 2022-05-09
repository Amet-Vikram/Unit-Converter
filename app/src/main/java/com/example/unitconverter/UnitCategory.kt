package com.example.unitconverter

class UnitCategory{
    enum class Length(val factor: Double){
        Meter(1.0),
        Feet(3.28084),
        Millimeters(1000.0);
    }

    enum class Volume(val factor : Double) {
        Litres(1.0),
        Gallons(0.264172),
        Millilitres(1000.0)
    }

    enum class Weight(val factor : Double) {
        Grams(1.0),
        Ounce(0.035274),
        Milligrams(1000.0)
    }

}
