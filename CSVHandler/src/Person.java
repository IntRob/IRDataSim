import definitions.BioClockType;
import definitions.Gender;


public class Person {

    private int id;

    private String decription;
    private int age;
    private Gender gender;
    private PersonaProfile persona;

    /* how social active - face to face, and speaks with others */
    private float socailAtt;

    /* how active and leaves the house */
    private float nonHomeAtt;

    /* how bored and is doing activities in the home such as reading, cooking, TV, etc.  */
    private float boredemAtt;

    /* when is the person most active and energized */
    private BioClockType bioType;

    /**
     * Represent how close (probability) the person is to the persona associated with him.
     * 0 - not at all
     * 1 - compeltely
     *
     * This is used to generate the specific behaviour of the person. it is guided by the community overall variance
    * */
    float varianceFromTemplate;

    public Person() {
    }

    public Person(int id,
                           int age,
                           Gender gender,
                           PersonaProfile persona,
                           float socailAtt,
                           float nonHomeAtt,
                           float boredemAtt,
                           float varianceFromTemplate,
                           BioClockType bio) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.persona = persona;
        this.socailAtt = socailAtt;
        this.nonHomeAtt = nonHomeAtt;
        this.boredemAtt = boredemAtt;
        this.varianceFromTemplate = varianceFromTemplate;
        this.bioType = bio;
    }

    @Override
    public String toString() {
        return "Persona details{" +
                "id=" + id +
                ", age=" + age +
                ", gender=" + gender.toString() +
                ", persona=" + persona.getID() +
                ", socailAtt=" + socailAtt +
                ", nonHomeAtt=" + nonHomeAtt+
                ", boredemAtt=" + boredemAtt +
                ", varianceFromTemplate=" + varianceFromTemplate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PersonaProfile getPersona() {
        return persona;
    }

    public void setPersona(PersonaProfile persona) {
        this.persona = persona;
    }

    public float getSocailAtt() {
        return socailAtt;
    }

    public void setSocailAtt(float socailAtt) {
        this.socailAtt = socailAtt;
    }

    public float getNonHomeAtt() {
        return nonHomeAtt;
    }

    public void setNonHomeAtt(float nonHomeAtt) {
        this.nonHomeAtt = nonHomeAtt;
    }

    public float getBoredemAtt() {
        return boredemAtt;
    }

    public void setBoredemAtt(float boredemAtt) {
        this.boredemAtt = boredemAtt;
    }

    public float getVarianceFromTemplate() {
        return varianceFromTemplate;
    }

    public void setVarianceFromTemplate(float varianceFromTemplate) {
        this.varianceFromTemplate = varianceFromTemplate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public BioClockType getBioType() {
        return bioType;
    }

    public void setBioType(BioClockType bioType) {
        this.bioType = bioType;
    }
}
