package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
@Service
public class RequestServiceV1 {
private final RequestsRepository requestsRepository;

	public RequestServiceV1( final @NotNull RequestsRepository requestsRepository ) {
		this.requestsRepository = Objects.requireNonNull(requestsRepository);
	}

	public Request newRequest( final Request newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntity> target = this.requestsRepository.findById( newRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_ALREADY_EXISTS, newRequest.getId().toString() );
			return new RequestEntityToRequestConverter().convert(
					this.requestsRepository.save(
							new RequestToRequestEntityConverter().convert(newRequest)
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}
}
