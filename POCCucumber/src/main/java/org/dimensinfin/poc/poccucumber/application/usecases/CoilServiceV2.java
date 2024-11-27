package org.dimensinfin.poc.poccucumber.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.dimensinfin.poc.poccucumber.domain.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	public List<Coil> getCoils() {
		final ArrayList<Coil> data = new ArrayList<Coil>();
		data.add( Coil.builder()
						.name( "TEST-COIL" )
				.build()
		);
		return data;
	}
}
