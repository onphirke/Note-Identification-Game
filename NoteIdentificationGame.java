import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class NoteIdentificationGame {
	public Mixer mixer;
	public Clip clip;
	public Methods methods;

	public NoteIdentificationGame() throws InterruptedException {

		Scanner input = new Scanner(System.in);

		setup();
		createMixer();
		while (true) {

			String letter = doClip();
			waitForClip();
			guessing(input, letter);

		}

	}

	public void setup() throws InterruptedException {

		System.out.println("Welcome to the Tone Game!");
		System.out.println(
				"Instructions: A note will be played in which you, the player, will decide what pitch it is from C4 - C5");
		Thread.sleep(1000);
		System.out.println("Ready");
		// Thread.sleep(1000);
		System.out.println("Set");
		Thread.sleep(1000);
		System.out.println("Go!");
		System.out.println();

	}

	public void createMixer() {
		// TODO Auto-generated method stub
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
	}

	public String doClip() {

		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip) mixer.getLine(dataInfo);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}

		String alphabet = "ABCDEFG";
		String letter = "";
		Random random = new Random();
		for (int i = 0; i < 1; i++) {
			char c = alphabet.charAt(random.nextInt(7));
			letter += c;
		}

		String note = "/MMC/" + letter + "4.wav";

		try {
			URL soundURL = NoteIdentificationGame.class.getResource(note);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		clip.start();

		return letter;

	}

	public void waitForClip() {

		do {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		} while (clip.isActive());

	}

	public void guessing(Scanner input, String letter) {

		System.out.println("Enter Guess (Make sure to capitalize): ");
		String guess = input.nextLine();

		if (guess.equals(letter)) {
			System.out.println("You're correct!");
			System.out.println();
		} else {
			System.out.println("You're wrong!");
			System.out.println();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		new NoteIdentificationGame();
	}

}
