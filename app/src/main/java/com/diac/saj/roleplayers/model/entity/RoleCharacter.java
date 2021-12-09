package com.diac.saj.roleplayers.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "character",
        indices = {@Index(value = "idclass"), @Index(value = "idrace")},
        foreignKeys = {@ForeignKey(entity = Race.class, parentColumns = "id", childColumns = "idrace", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = RoleClass.class, parentColumns = "id", childColumns = "idclass", onDelete = ForeignKey.CASCADE)})
public class RoleCharacter {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idclass")
    public long idclass;

    @NonNull
    @ColumnInfo(name = "idrace")
    public long idrace;

    @NonNull
    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "state")
    public String state;


    @ColumnInfo(name = "creation")
    public String creation;

    @ColumnInfo(name = "strength")
    public int strength;


    @ColumnInfo(name = "dexterity")
    public int dexterity;

@ColumnInfo(name = "constitution")
    public int constitution;


    @ColumnInfo(name = "intelligence")
    public int intelligence;


    @ColumnInfo(name = "wisdom")
    public int wisdom;


    @ColumnInfo(name = "charisma")
    public int charisma;


    public boolean isValid(){
        return level >0 && !(name.isEmpty() || creation.isEmpty() || state.isEmpty());
    }

}
