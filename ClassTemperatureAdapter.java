interface MetricTemperatureSensor {
    double getTemperatureCelsius();
}
class FahrenheitSensor {
    public double readFahrenheit() {
        return 98.6;
    }
}
public class ClassTemperatureAdapter extends FahrenheitSensor implements MetricTemperatureSensor {

    @Override
    public double getTemperatureCelsius() {
        double fahrenheit = readFahrenheit();
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
    public static void main(String[] args) {
        String studentId = "240103008"; 
        System.out.println("Student ID: " + studentId);

        MetricTemperatureSensor classAdapter = new ClassTemperatureAdapter();
        double classTemp = classAdapter.getTemperatureCelsius();

        FahrenheitSensor sensor = new FahrenheitSensor();
        MetricTemperatureSensor objectAdapter = new ObjectTemperatureAdapter(sensor);
        double objectTemp = objectAdapter.getTemperatureCelsius();

        System.out.printf("Class Adapter Celsius: %.2f°C%n", classTemp);
        System.out.printf("Object Adapter Celsius: %.2f°C%n", objectTemp);
        System.out.println("Values are identical: " + (classTemp == objectTemp));
    }
}