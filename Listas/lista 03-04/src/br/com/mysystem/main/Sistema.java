package br.com.mysystem.main;
import br.com.mysystem.model.Carro;

public class Sistema{
    public static void main(String[] args){
        System.out.println("Inicializando o sistema...");
        Carro myCar = new Carro();
        myCar.setModelo("Fusca");
        myCar.setCor("Azul");
        myCar.buzinar();


        Carro carro2 = new Carro();
        carro2.setModelo("Lamborghini");
        carro2.setCor("Preto");
        carro2.setVelocidade(-500);
        carro2.buzinar();   
    }
}
