package pro1;

import com.google.gson.annotations.SerializedName;

public class ScheduleItem
{
    @SerializedName("obsazeni")
    int personsCount;
    @SerializedName("nazev")
    String title;
    @SerializedName("katedra")
    String department;
}
