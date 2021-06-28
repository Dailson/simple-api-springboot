package com.dailson.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dailson.api.domain.Anime;
import com.dailson.api.requests.AnimeGetRequestBody;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

	public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
	

	public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
	
	public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
	
	public abstract Anime toAnime(AnimeGetRequestBody animeGetRequestBody);
	
}
