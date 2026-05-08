/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tampilan;

/**
 *
 * @author Asus
 */
public class exceptionData extends Exception {
    public exceptionData(){
    }

    exceptionData(String semua_field_bobot_harus_diisi) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public String showMessageError(){
         String notice = "Silahkan isi data dengan lengkap!";
        return notice;
    }
}
