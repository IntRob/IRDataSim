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

    private double attToSkype = 0.5;
    private double attToMusic = 0.5;
    private double attToTV = 0.5;
    private double attToBook = 0.5;
    private double attToPhysical = 0.5;
    private double attToSocial = 0.5;


    /**
     * Attitude to routine defines how close this persona follows its routine.
     * Scale from 0-1
     * 1 always
     * 0 never
     */

    private double attToRoutine;

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

    public double getAttToSkype() {
        return attToSkype;
    }

    public void setAttToPhone(double attToSkype) {
        this.attToSkype = attToSkype;
    }

    public double getAttToMusic() {
        return attToMusic;
    }

    public void setAttToMusic(double attToMusic) {
        this.attToMusic = attToMusic;
    }

    public double getAttToTV() {
        return attToTV;
    }

    public void setAttToTV(double attToTV) {
        this.attToTV = attToTV;
    }

    public double getAttToBook() {
        return attToBook;
    }

    public void setAttToBook(double attToBook) {
        this.attToBook = attToBook;
    }

    public double getAttToPhysical() {
        return attToPhysical;
    }

    public void setAttToPhysical(double attToPhysical) {
        this.attToPhysical = attToPhysical;
    }

    public double getAttToSocial() {
        return attToSocial;
    }

    public void setAttToSocial(double attToSocial) {
        this.attToSocial = attToSocial;
    }

    public double getAttToRoutine() {
        return attToRoutine;
    }

    public void setAttToRoutine(double attToRoutine) {
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
                "\n" + ", attToSkype=" + attToSkype +
                ", attToMusic=" + attToMusic +
                ", attToTV=" + attToTV +
                ", attToBook=" + attToBook +
                ", attToPhysical=" + attToPhysical +
                ", attToSocial=" + attToSocial +
                ", attToRoutine=" + attToRoutine +
                '}';
    }
}
