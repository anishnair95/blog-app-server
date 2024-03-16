package com.springboot.blog.mapper;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostDtoV2;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDtoV2 fromPostDto(PostDto postDto);
}