package com.dailson.api.util;

import java.util.ArrayList;
import java.util.List;

import com.dailson.api.domain.Anime;
import com.github.javafaker.Faker;

public class AnimeUtil {

	private static Faker fake = new Faker();
	
	public static Anime createOneAnime() {
		String name = fake.name().firstName();
		String url = fake.company().url();
		return new Anime(null, name, url);
	}

	public static Anime createOneEmptyAnime() {
		return new Anime();
	}
	
	public static List<Anime> createListOfAnime(int quantityOfAnime) {
		List<Anime> listAnime = new ArrayList<>();
		for (int i = 0; i < quantityOfAnime; i++) {
			listAnime.add(new Anime(null, fake.name().firstName(), fake.company().url()));
		}
		return listAnime;
	}
}
