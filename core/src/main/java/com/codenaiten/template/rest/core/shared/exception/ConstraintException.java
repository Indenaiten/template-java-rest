package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

import java.util.List;

@Getter
public class ConstraintException extends AppException{

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_CONSTRAINT_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\

    private final List<ConstraintViolation<?>> violations;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public ConstraintException( final ConstraintViolation<?>... violation ){
        super( DEFAULT.getMessage() );
        this.violations = List.of( violation );
    }

    public ConstraintException( final List<ConstraintViolation<?>> violations ){
        super( DEFAULT.getMessage() );
        this.violations = violations;
    }

    public ConstraintException( final Throwable cause, final ConstraintViolation<?>... violation ){
        super( cause, DEFAULT.getMessage() );
        this.violations = List.of( violation );
    }

    public ConstraintException( final Throwable cause, final List<ConstraintViolation<?>> violations ){
        super( cause, DEFAULT.getMessage() );
        this.violations = violations;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
