/**
 * Created by admin on 11/11/15.
 *
 * Represent the different personas/typecast of people and their daily routine
 *
 */


public class PersonaProfile {

    /* unique ID representing the persona type */
    private int typeID;

    /* unique name representing the persona type */
    private String name;

    /* Short description of the type cast */
    private String typeDescriptor;

    /* the person daily "template" routine for weekday and weekend */
    private DailyTemplate weekdayRoutine;
    private DailyTemplate weekendRoutine;


    /**
     * For each potential kYou suggested activity the attitude of the persona for each activity is defined
     * Scale from 0-10
     * 0 - like it at all. will never do it.
     * 10 - like it a lot. will always do it.
     *
     * Potential events include: DOPHONE, DOMUSIC, DOTV, DOBOOK, DOPHYSICAL, DOSOCIAL
     */

    private int attToPhone;
    private int attToMusic;
    private int attToTV;
    private int attToBook;
    private int attToPhysical;
    private int attToSocial;


    /**
     * Attitude to routine defines how close this persona follows its routine.
     * Scale from 0-10
     * 0 - never
     * 10 - always
     */

    private int attToRoutine;

    public PersonaProfile() {
    }

    public PersonaProfile(int typeID, String typeDescriptor, DailyTemplate weekdayRoutine, DailyTemplate weekendRoutine) {
        this.typeID = typeID;
        this.typeDescriptor = typeDescriptor;
        this.weekdayRoutine = weekdayRoutine;
        this.weekendRoutine = weekendRoutine;
    }

    public int getID() {
        return typeID;
    }

    public void setID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeDescriptor() {
        return typeDescriptor;
    }

    public void setTypeDescriptor(String typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
    }

    public DailyTemplate getWeekdayRoutine() {
        return weekdayRoutine;
    }

    public void setWeekdayRoutine(DailyTemplate weekdayRoutine) {
        this.weekdayRoutine = weekdayRoutine;
    }

    public DailyTemplate getWeekendRoutine() {
        return weekendRoutine;
    }

    public void setWeekendRoutine(DailyTemplate weekendRoutine) {
        this.weekendRoutine = weekendRoutine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttToPhone() {
        return attToPhone;
    }

    public void setAttToPhone(int attToPhond) {
        this.attToPhone = attToPhond;
    }

    public int getAttToMusic() {
        return attToMusic;
    }

    public void setAttToMusic(int attToMusic) {
        this.attToMusic = attToMusic;
    }

    public int getAttToTV() {
        return attToTV;
    }

    public void setAttToTV(int attToTV) {
        this.attToTV = attToTV;
    }

    public int getAttToBook() {
        return attToBook;
    }

    public void setAttToBook(int attToBook) {
        this.attToBook = attToBook;
    }

    public int getAttToPhysical() {
        return attToPhysical;
    }

    public void setAttToPhysical(int attToPhysical) {
        this.attToPhysical = attToPhysical;
    }

    public int getAttToSocial() {
        return attToSocial;
    }

    public void setAttToSocial(int attToSocial) {
        this.attToSocial = attToSocial;
    }

    public int getAttToRoutine() {
        return attToRoutine;
    }

    public void setAttToRoutine(int attToRoutine) {
        this.attToRoutine = attToRoutine;
    }

    @Override
    public String toString() {
        return "PersonaProfile{" +
                "typeID=" + typeID +
                ", name='" + name + '\'' +
                ", typeDescriptor='" + typeDescriptor + '\'' +
                ", weekdayRoutine=" + weekdayRoutine +
                ", weekendRoutine=" + weekendRoutine +
                "\n" + ", attToPhone=" + attToPhone +
                ", attToMusic=" + attToMusic +
                ", attToTV=" + attToTV +
                ", attToBook=" + attToBook +
                ", attToPhysical=" + attToPhysical +
                ", attToSocial=" + attToSocial +
                ", attToRoutine=" + attToRoutine +
                '}';
    }
}
