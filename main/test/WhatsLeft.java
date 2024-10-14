import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WhatsLeft {
	public ArrayList<Player> players = new ArrayList<>();
	public ArrayList<Pile> piles = new ArrayList<>();
    public ServerSocket aSocket;





	
	




	public WhatsLeft(String[] args) {
		

		// Set random starting player
		int currentPlayer = (int) (Math.random() * (players.size()));
		

	}

	public static void main(String[] args) {
		PointSalad game = new PointSalad(args);

	}
}