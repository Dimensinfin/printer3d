package org.dimensinfin.poc.application.inventory.coil.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.dimensinfin.poc.domain.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	public List<Coil> getCoils() {
		return new ArrayList<Coil>();
	}
}
