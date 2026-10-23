public class ObjectTemperatureAdapter implements MetricTemperatureSensor {
    private FahrenheitSensor fahrenheitSensor;

    public ObjectTemperatureAdapter(FahrenheitSensor fahrenheitSensor) {
        this.fahrenheitSensor = fahrenheitSensor;
    }

    @Override
    public double getTemperatureCelsius() {
        double fahrenheit = fahrenheitSensor.readFahrenheit();
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
}