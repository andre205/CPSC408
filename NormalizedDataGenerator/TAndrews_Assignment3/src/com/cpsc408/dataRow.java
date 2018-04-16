/**

 @author Tyler Andrews

 This is a simple dataRow class.

 It stores the data from an entire row of an unnormalized csv

 */

package com.cpsc408;

//For reference: the python used to generate a data row is:
//int_out.append(m.Person().username())
//int_out.append(m.Person().name())
//int_out.append(m.Person().last_name())
//int_out.append(m.Person().telephone())
//int_out.append(m.Address().address())
//int_out.append(m.Address().zip_code())
//int_out.append(m.Datetime().date())
//
//int_out.append(m.Datetime().date())
//
//int_out.append(m.Hardware().cpu())
//int_out.append(m.Hardware().cpu_frequency())
//int_out.append(m.Hardware().graphics())
//int_out.append(m.Hardware().manufacturer())
//int_out.append(m.Hardware().ram_size())
//int_out.append(m.Hardware().ram_type())
//int_out.append(m.Hardware().resolution())
//
//int_out.append(m.Games().game())
//int_out.append(m.Datetime().date())
//int_out.append(m.Text().text(2))
//
//int_out.append(m.Text().text(3))
//int_out.append(m.Text().level())

public class dataRow {

    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String address;
    private String zip;
    private String dob;
    private String purchaseDate;
    private String cpu;
    private String cpuFreq;
    private String graphicsCard;
    private String manufacturer;
    private String ramSize;
    private String ramType;
    private String resolution;
    private String gameName;
    private String releaseDate;
    private String description;
    private String text;
    private String level;

    public dataRow(String username, String firstName, String lastName, String telephone, String address, String zip, String dob, String purchaseDate, String cpu, String cpuFreq, String graphicsCard, String manufacturer, String ramSize, String ramType, String resolution, String gameName, String releaseDate, String description, String text, String level) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
        this.zip = zip;
        this.dob = dob;
        this.purchaseDate = purchaseDate;
        this.cpu = cpu;
        this.cpuFreq = cpuFreq;
        this.graphicsCard = graphicsCard;
        this.manufacturer = manufacturer;
        this.ramSize = ramSize;
        this.ramType = ramType;
        this.resolution = resolution;
        this.gameName = gameName;
        this.releaseDate = releaseDate;
        this.description = description;
        this.text = text;
        this.level = level;
    }

    @Override
    public String toString() {
        return "dataRow{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", dob='" + dob + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", cpu='" + cpu + '\'' +
                ", cpuFreq='" + cpuFreq + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", ramSize='" + ramSize + '\'' +
                ", ramType='" + ramType + '\'' +
                ", resolution='" + resolution + '\'' +
                ", gameName='" + gameName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", description='" + description + '\'' +
                ", text='" + text + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getDob() {
        return dob;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getCpu() {
        return cpu;
    }

    public String getCpuFreq() {
        return cpuFreq;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getRamSize() {
        return ramSize;
    }

    public String getRamType() {
        return ramType;
    }

    public String getResolution() {
        return resolution;
    }

    public String getGameName() {
        return gameName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public String getLevel() {
        return level;
    }

}
