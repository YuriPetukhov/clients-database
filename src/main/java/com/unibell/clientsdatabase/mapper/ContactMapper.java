package com.unibell.clientsdatabase.mapper;

import com.unibell.clientsdatabase.dto.*;
import com.unibell.clientsdatabase.entity.Contact;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ContactMapper {

    ClientContact toDtoClientContact(Contact contact);

    ClientContactByType toDtoClientContactByType(Contact contact);

}
