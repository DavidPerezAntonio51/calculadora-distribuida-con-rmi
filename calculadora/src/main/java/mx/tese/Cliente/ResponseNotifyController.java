package mx.tese.Cliente;

import mx.tese.CalculadoraCore.Respuesta;

import javax.swing.*;

public class ResponseNotifyController {
    private final JFrame context;

    public ResponseNotifyController(JFrame context) {
        this.context = context;
    }

    public void notify(Respuesta response){
        if(response.success()){
            String format = String.format("El resultado es: %s", response.getResultado());
            JOptionPane.showOptionDialog(context,format,"Resultado",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
        }
        else{
            JOptionPane.showOptionDialog(context,response.getError(),"Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,null,null);
        }
    }
}
