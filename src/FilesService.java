import java.io.*;

public class FilesService {
	
	public static boolean writeObj(Serializable obj ,String path){
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(path)));)
		{
			out.writeObject(obj);
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static Serializable readObj(){
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("/var/www/www.yumbox.ir/YumBoxBotUsers.txt"))))
		{
			return (Serializable) in.readObject();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
