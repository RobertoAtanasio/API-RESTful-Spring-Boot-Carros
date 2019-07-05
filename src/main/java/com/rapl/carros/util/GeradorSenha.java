package com.rapl.carros.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {

//		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghiklmnopqrstuvxywz0123456789+-*/=;.@#$%&()";
//		 
//		Random random = new Random();
//		 
//		String armazenaChaves = "";
//		int index = -1;
//		for( int i = 0; i < 40; i++ ) {
//		   index = random.nextInt( letras.length() );
//		   armazenaChaves += letras.substring( index, index + 1 );
//		}
//		System.out.println( armazenaChaves );
		
//		LocalDateTime localDateTime = LocalDateTime.now();
//		LocalDateTime timeOutro = localDateTime.plusHours(1).plusMinutes(1).plusSeconds(1);
//		LocalDateTime timeOutro2 = localDateTime.plusMinutes(5);
//		System.out.println(localDateTime);
//		System.out.println(timeOutro);
//		System.out.println(timeOutro2);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("roberto"));
		
	}
}
