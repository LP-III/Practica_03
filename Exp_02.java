interface Notification {
    void send(String message);
}

 class SMSNotification implements Notification{
	 @Override
	 public void send(String message) {
        System.out.println("Enviando SMS: " + message);
    }
}

class EmailNotification implements Notification{
	@Override
	public void send (String menssage) {
		System.out.print("Enviando correo: " + menssage);
	}
}

public class NotificationManager{
	public static void main(String[]args) {
		EmailNotification emailNotification = new
		EmailNotification();
		SMSNotification smsNotification = new
		SMSNotification();
		
		emailNotification.send("¡Hola! Este es un mensaje de prueba");
		smsNotification.send("¡Hola! Este es un SMS de prueba");
	}
}