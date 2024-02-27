package com.unibell.clientsdatabase.dto;

import com.unibell.clientsdatabase.enums.ContactType;
import lombok.Data;

@Data
public class ClientContact {
    private ContactType type;
    private String value;
}
