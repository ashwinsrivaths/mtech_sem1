package com.uis.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Playlist {

    ArrayList<Song> songs = new ArrayList<>();
    static Scanner sc1 = new Scanner(System.in);

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void run() {
        int select = 0;

        while (select != 5) {
            System.out.println(
                    "Welcome to the Playlist Manager! enter the choice: \n1. Add a song\n2. Remove a song\n3. View playlist\n4. Search for a song\n5. Exit");

            try {
                select = Integer.parseInt(sc1.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer option.");
            }

            switch (select) {
                case 5:
                    System.out.println("Exiting the Playlist Manager. Goodbye!");
                    break;
                case 4:
                    System.out.println("You selected option: " + select);
                    // Code to search for a song
                    String searchQuery = sc1.nextLine().trim();
                    for (Song song : this.songs) {
                        if (song.title.contains(searchQuery)) {
                            System.out.println("Found song: " + song.title + " by " + song.artist);
                        }
                    }

                    // iterator method to search for a song
                    Iterator<Song> itr = this.songs.iterator();
                    while (itr.hasNext()) {
                        Song song = itr.next();
                        if (song.title.contains(searchQuery)) {
                            System.out.println("Found song: " + song.title + " by " + song.artist);
                        }
                    }
                    break;

                case 3:
                    System.out.println(this.songs);
                    break;
                case 1:
                    System.out.println("enter title");
                    String title = sc1.nextLine().trim();
                    System.out.println("enter artist");
                    String artist = sc1.nextLine().trim();
                    System.out.println("enter duration");
                    int duration = Integer.parseInt(sc1.nextLine());
                    this.songs.add(new Song(title, artist, duration));
                    break;

                case 6:
                    System.out.println("You selected option: " + select);
                    System.out.println("sort based on: \n1. Title\n2. Artist\n3. Duration");
                    int sortOrder = 0;
                    try {
                        sortOrder = Integer.parseInt(sc1.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid integer option.");
                    }

                    switch (sortOrder) {
                        case 1:
                            // Sort by title
                            Collections.sort(this.songs, new SongTitleComparator());
                            break;
                        case 2:
                            // Sort by artist
                            Collections.sort(this.songs, new SongAuthorComparator());
                            break;
                        case 3:
                            // Sort by duration
                            Collections.sort(this.songs);
                            break;
                        default:
                            System.out.println("Invalid sort option. Please enter a valid integer option.");
                            break;
                    }
                    break;

                case 7:
                    System.out.println("You selected option: " + select);

                    break;

                default:
                    System.out.println("You selected option: " + select);
                    System.out.println("unique words");

                    String input = sc1.nextLine().trim();
                    HashSet<String> s1 = new HashSet<String>();
                    HashSet<String> s2 = new HashSet<String>();

                    for (String word: input.split(" ")) {
                        if (s1.add(word)) s2.add(word);
                    }
                    s1.removeAll(s2);

                    System.out.println("Unique words: " + s1);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        playlist.addSong(new Song("Shape of You", "Ed Sheeran", 240));
        playlist.addSong(new Song("Blinding Lights", "The Weeknd", 200));
        playlist.addSong(new Song("Bohemian Rhapsody", "Queen", 360));
        playlist.addSong(new Song("Stairway to Heaven", "Led Zeppelin", 480));
        playlist.addSong(new Song("Imagine", "John Lennon", 180));
        playlist.addSong(new Song("Imagine", "John Lennon", 180));
        playlist.addSong(new Song("Imagine", "John Lennon", 180));
        playlist.addSong(new Song("Imagine", "John Lennon", 180));

        playlist.run();
    }
}

class Song implements Comparable<Song> {
    String title;
    String artist;
    int duration; // duration in seconds

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                '}';

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((artist == null) ? 0 : artist.hashCode());
        result = prime * result + duration;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (artist == null) {
            if (other.artist != null)
                return false;
        } else if (!artist.equals(other.artist))
            return false;
        if (duration != other.duration)
            return false;
        return true;
    }

    @Override
    public int compareTo(Song s) {
        return this.duration - s.duration;
    }

}

class SongTitleComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return s1.title.compareTo(s2.title);
    }
}

class SongAuthorComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Song && o2 instanceof Song) {
            Song s1 = (Song) o1;
            Song s2 = (Song) o2;
            return s1.artist.compareTo(s2.artist);
        } else {
            throw new IllegalArgumentException("Both objects must be of type Song");
        }
    }
}