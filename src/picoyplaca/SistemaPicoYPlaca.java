/*finalv2
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picoyplaca;

import com.toedter.calendar.JDateChooser;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class SistemaPicoYPlaca {

    //Esta funcion me permite validar la placa ingresada por el usuario ya que puede ser de 6 o 7 dígitos
    void validacionPlaca(JTextField TextField_placa, JLabel Label_error1, JLabel Label_error2) {
        String[] placaTemporal = new String[7]; // definimos una placa temporal para almacenar los datos de la placa que se van a verificar con las expresiones regulares
        String texto=(TextField_placa.getText());//Obtenemos la placa ingresada por el usuario
        Pattern patron1= Pattern.compile("[a-zA-Z]"); //utilizaremos la funcion patterns para asegurarnos que los 3 primeros dígitos de la placa sean letras mayúsculas o minúsculas y el resto sea sólo números a traves de expresiones regulares
        Pattern patron2= Pattern.compile("[0-9]");
        Matcher matcher;
        //System.out.print(texto);
            if(texto.length()==6){ //Aqui aumentamos un caracter a la cadena para identificar cuando el ingreso de la placa solo fue de 6 dígitos
               texto=texto+"0";
            }
        char [] numeroPlaca= texto.toCharArray();// Transformamos el texto de la placa en array para poder validar la placa 
       //  System.out.print(numeroPlaca.length);
            if(numeroPlaca.length==6){ //si la placa solo fue de 6 digitos entonces tiene una b al final lo cual hay que remplazar por el dígito anterior el cual servirá para determinar si hay o no pico y placa
               numeroPlaca[6]=numeroPlaca[5];        
            }
       
            if (numeroPlaca.length==7){  // esta condicion solo se va a ejecutar cuando el usuario digite una placa completa, caso contrario se mostraran los mensajes de error
                for(int i=0;i<=2;i++){ //aqui verificamos las letras de la placa
                    placaTemporal[i]=Character.toString(numeroPlaca[i]);// transformamos la placa a string por que la funcion matcher solo acepta string como parámetros
                 // System.out.println(placaTemporal[5]);
                //  System.out.println(placaTemporal[6]);
                  
                    matcher = patron1.matcher(placaTemporal[i]);
                    if(!matcher.find()){ //si no se encuentran letras se muestran los mensajes de error
                         Label_error1.setVisible(true);
                         Label_error2.setVisible(true);
                      //    System.out.print("esta entrando por aqui5");
                         break;
                    }
                    else { //reestablecemos los mensajes de error en caso de hacer nuevas consultas
                        Label_error1.setVisible(false);
                        Label_error2.setVisible(false);
                       //  System.out.print("esta entrando por aqui4");
                    }
            
                }
                for(int j=3;j<7;j++){ //aqui verificamos los números de la placa
                    placaTemporal[j]=Character.toString(numeroPlaca[j]); //transformamos la placa en string debido a que la funcion matcher solo acepta strings como parámetros
                //   System.out.print(placaTemporal[6]);
                    matcher = patron2.matcher(placaTemporal[j]);
                    if(!matcher.find()){ // si no encuentra los numeros establecidos en las expresiones regulares, se muestran los mensajes de error
                     Label_error1.setVisible(true);
                     Label_error2.setVisible(true);
                      break;
                    }
                    else { //reestablecemos los mensajes de error en caso de realizar nuevas consultas
                     Label_error1.setVisible(false);
                     Label_error2.setVisible(false);
             //        System.out.print("esta entrando por aqui2");
                    }
                }
            }
            else { // si la placa tiene una longitud diferente a 7 se muestran los mensajes de error
          
             Label_error1.setVisible(true);
             Label_error2.setVisible(true);
           // System.out.print("esta entrando por aqui1");
            }
   
       
    }
    
    void verificacionDeRestriccion(JDateChooser Calendario, JComboBox<String> ComboBox_horarios,JTextField TextField_placa,JLabel Label_resultados, JLabel Label_error1, JLabel Label_error2) {
        String[] placaTemporal = new String[7]; // definimos una placa temporal para almacenar los datos de la placa que se van a verificar con las expresiones regulares
        String texto=(TextField_placa.getText());//Obtenemos la placa ingresada por el usuario
        int diaDeSemana=Calendario.getCalendar().getTime().getDay();
        int ultimoDigito=0;
           if(texto.length()==6){ //Aqui aumentamos un caracter a la cadena para identificar cuando el ingreso de la placa solo fue de 6 dígitos
               texto=texto+"b";
            }
        char [] placa= texto.toCharArray();// Transformamos el texto de la placa en array para poder validar la placa 
           if(placa.length!=7){
               Label_error1.setVisible(true);
               Label_error2.setVisible(true);
            }
            else{
            if(placa[6]=='b'){ //si la placa solo fue de 6 digitos entonces tiene una b al final lo cual hay que remplazar por el dígito anterior el cual servirá para determinar si hay o no pico y placa
               placa[6]=placa[5];
               //System.out.print(placa[6]);
            }}  
            if(placa.length==7){ //si la placa tiene 7 dígitos tomamos el último digito para comparar, caso contrario no entrara al switch case
               ultimoDigito=Character.getNumericValue(placa[6]);}
            else{
               diaDeSemana=8;
            }
        switch (diaDeSemana){
           case 0:// el caso 0 corresponde al dia domingo
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
           break;
           case 1://el caso 1 corresponde al dia lunes
                if(ultimoDigito==1||ultimoDigito==2){
                    if(ComboBox_horarios.getSelectedItem()=="07h00-09h30"||ComboBox_horarios.getSelectedItem()=="16h00-19h30"){ //Se compara la eleccion del usuario con el horario de restricción
                    Label_resultados.setForeground(red);
                    Label_resultados.setText("USTED NO PUEDE CIRCULAR");
                    }
                    else{
                    Label_resultados.setForeground(green);
                    Label_resultados.setText("USTED PUEDE CIRCULAR");
                    }
                }
                else{
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
                }
            break;
          case 2:// el caso 2 corresponde al día martes
               if(ultimoDigito==3||ultimoDigito==4){
                    if(ComboBox_horarios.getSelectedItem()=="07h00-09h30"||ComboBox_horarios.getSelectedItem()=="16h00-19h30"){
                    Label_resultados.setForeground(red);
                    Label_resultados.setText("USTED NO PUEDE CIRCULAR");
                    }
                    else{
                    Label_resultados.setForeground(green);
                    Label_resultados.setText("USTED PUEDE CIRCULAR");
                    }
                }
                else{
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
                 }
            break;
          case 3://el caso 3 corresponde al día miercoles
               if(ultimoDigito==5||ultimoDigito==6){
                    if(ComboBox_horarios.getSelectedItem()=="07h00-09h30"||ComboBox_horarios.getSelectedItem()=="16h00-19h30"){
                    Label_resultados.setForeground(red);
                    Label_resultados.setText("USTED NO PUEDE CIRCULAR");
                    }
                    else{
                    Label_resultados.setForeground(green);
                    Label_resultados.setText("USTED PUEDE CIRCULAR");
                    }
                }
                else{
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
                }
            break;
          case 4://el caso 4 corresponde al día jueves
               if(ultimoDigito==7||ultimoDigito==8){
                    if(ComboBox_horarios.getSelectedItem()=="07h00-09h30"||ComboBox_horarios.getSelectedItem()=="16h00-19h30"){
                    Label_resultados.setForeground(red);
                    Label_resultados.setText("USTED NO PUEDE CIRCULAR");
                    }
                    else{
                    Label_resultados.setForeground(green);
                    Label_resultados.setText("USTED PUEDE CIRCULAR");
                    }
                }
                else{
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
                }
            break;
          case 5:// el caso 5 corresponde al día viernes
               if(ultimoDigito==9||ultimoDigito==0){
                    if(ComboBox_horarios.getSelectedItem()=="07h00-09h30"||ComboBox_horarios.getSelectedItem()=="16h00-19h30"){
                    Label_resultados.setForeground(red);
                    Label_resultados.setText("USTED NO PUEDE CIRCULAR"); 
                    }
                    else{
                    Label_resultados.setForeground(green);
                    Label_resultados.setText("USTED PUEDE CIRCULAR");
                    }
                }
                else{
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
                }
            break;
            case 6:// el caso 6 corresponde al día sabado
                Label_resultados.setForeground(green);
                Label_resultados.setText("USTED PUEDE CIRCULAR");
            break;  
           
 }
  
    }

}