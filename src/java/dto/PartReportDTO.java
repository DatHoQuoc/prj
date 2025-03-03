/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author datho
 */
public class PartReportDTO {
    private long partID;
    private String partName;
    private int used_parts;

    public PartReportDTO(long partID, String partName, int used_parts) {
        this.partID = partID;
        this.partName = partName;
        this.used_parts = used_parts;
    }

    public long getPartID() {
        return partID;
    }

    public void setPartID(long partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getUsed_parts() {
        return used_parts;
    }

    public void setUsed_parts(int used_parts) {
        this.used_parts = used_parts;
    }
    
    
}
