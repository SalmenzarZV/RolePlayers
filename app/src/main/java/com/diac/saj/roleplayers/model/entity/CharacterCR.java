package com.diac.saj.roleplayers.model.entity;

import androidx.room.Embedded;

public class CharacterCR {

    @Embedded
    public RoleCharacter character;

    @Embedded(prefix="class_")
    public RoleClass roleclass;

    @Embedded(prefix="race_")
    public Race race;
}
