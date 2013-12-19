package com.gexcat.gex.model.find;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.Transient;

import com.apl.base.annotation.AnnotationsUtil;
import com.apl.base.aspect.AplLogger;
import com.apl.base.exception.InfrastructureException;
import com.apl.base.model.AbstractPersistent;
import com.apl.base.tool.MiscBase;
import com.apl.components.misc.Misc;
import com.gexcat.gex.misc.GexCatMisc;
import com.gexcat.gex.misc.UserData;
import com.gexcat.gex.model.Alumno;
import com.gexcat.gex.model.Domicilio;
import com.gexcat.gex.model.type.TipoSexo;


/**
 * <p>Clase utilizada para la importación de alumnos del fichero CSV</p>
 *
 * @author  jesus
 * @author  jose
 */
public class AlumnoImporter
    extends Alumno {

    private static final long serialVersionUID = 5637591518081400576L;

    private String apellido1;
    private String apellido2;
    
    private boolean generado;
    private boolean duplicado;

    public AlumnoImporter() {
        setTrabaja(false);
        setEnviarCorreo(true);
    }

    @Override
    public void setDni(final String codigo) {
        super.setDni(trim(codigo));
        if (getIdentificador() == null && getDni() != null){
        	setIdentificador(GexCatMisc.getIdentificador(getDni()));
        	setGenerado(true);
        }
    }
    
    
    public void setIdentificacion(final String identificacion) {
       	try{
       		if (MiscBase.parseInteger(identificacion) != null) {
       			setGenerado(false);
       			super.setIdentificador(MiscBase.parseInteger(identificacion));
       		}
       	} catch (final InfrastructureException e) {
            AplLogger.LOG.info(e.getLocalizedMessage());
        }
    	
    }
    
    @Override
    public void setNiu(final String niu) {
        super.setNiu(trim(niu));
    }

    @Override
    public void setNombre(final String nombre) {
        super.setNombre(trim(nombre));
    }
    
    public boolean isGenerado() {
		return generado;
	}

	public void setGenerado(final boolean generado) {
		this.generado = generado;
	}

	public boolean isDuplicado() {
		return duplicado;
	}

	public void setDuplicado(final boolean duplicado) {
		this.duplicado = duplicado;
	}

	@Override
    public void setApellidos(final String apellidos) {
        super.setApellidos(trim(apellidos));
    }

    @Override
    public void setCorreo(final String correo) {
        super.setCorreo(trim(correo));
    }

    @Override
    public void setMovil(final String movil) {
        super.setMovil(trim(movil));
    }

    @Override
    public void setObservaciones(final String observaciones) {
        super.setObservaciones(trim(observaciones));
    }

    private String trim(final String text) {

        if (text == null || text.trim().length() == 0) {
            return null;
        } else {
            return text.trim();
        }
    }

    @Transient
    public void setTrabajador(final String trabajador) {

        if ("S".equalsIgnoreCase(trabajador)) {
            setTrabaja(true);
        } else if ("N".equalsIgnoreCase(trabajador)) {
            setTrabaja(false);
        }
    }

    @Transient
    public void setTipo_Sexo(final String tipo_sexo) {

        if ("M".equalsIgnoreCase(tipo_sexo)) {
            setSexo(TipoSexo.valueOf("MASCULINO"));
        } else if ("F".equalsIgnoreCase(tipo_sexo)) {
            setSexo(TipoSexo.valueOf("FEMENINO"));
        } else {
            setSexo(TipoSexo.valueOf("INDEFINIDO"));
        }

    }

    @Transient
    public void setDc_Direccion(final String dc_direccion) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setDireccion(dc_direccion);
    }

    @Transient
    public void setDc_Provincia(final String dc_provincia) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setProvincia(dc_provincia);

    }

    @Transient
    public void setDc_Localidad(final String dc_localidad) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setLocalidad(dc_localidad);
    }

    @Transient
    public void setDc_Codigo_Postal(final String dc_codigo_postal) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setCodigoPostal(dc_codigo_postal);
    }

    @Transient
    public void setDc_Pais(final String dc_pais) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setPais(dc_pais);
    }

    @Transient
    public void setDc_Telefono(final String dc_telefono) {

        if (getDomicilioCurso() == null) {
            setDomicilioCurso(new Domicilio());
        }

        getDomicilioCurso().setTelefono(dc_telefono);
    }

    @Transient
    public void setDf_Direccion(final String df_direccion) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setDireccion(df_direccion);
    }

    @Transient
    public void setDf_Provincia(final String df_provincia) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setProvincia(df_provincia);

    }

    @Transient
    public void setDf_Localidad(final String df_localidad) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setLocalidad(df_localidad);
    }

    @Transient
    public void setDf_Codigo_Postal(final String df_codigo_postal) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setCodigoPostal(df_codigo_postal);
    }

    @Transient
    public void setDf_Pais(final String df_pais) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setPais(df_pais);
    }

    @Transient
    public void setDf_Telefono(final String df_telefono) {

        if (getDomicilioFamiliar() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioFamiliar().setTelefono(df_telefono);
    }

    @Transient
    public void setDn_Provincia(final String dn_provincia) {

        if (getDomicilioNacimiento() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioNacimiento().setProvincia(dn_provincia);

    }

    @Transient
    public void setDn_Localidad(final String dn_localidad) {

        if (getDomicilioNacimiento() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioNacimiento().setLocalidad(dn_localidad);
    }

    @Transient
    public void setDn_Pais(final String dn_pais) {

        if (getDomicilioNacimiento() == null) {
            setDomicilioFamiliar(new Domicilio());
        }

        getDomicilioNacimiento().setPais(dn_pais);
    }

    @Transient
    public void setFecha_Nacimiento(final String fecha_nacimiento) {
        final SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
                "dd/MM/yyyy");
        Date fecha = null;

        try {
            fecha = formatoDelTexto.parse(fecha_nacimiento);
            setFechaNacimiento(fecha);
        } catch (final ParseException e) {
            AplLogger.LOG.info(e.getLocalizedMessage());
        }
    }

    @Transient
    public void setInicio_Estudios(final String inicio_estudios) {
        setInicioEstudios(inicio_estudios);
    }

    @Transient
    public void setEstudios_Anteriores(final String estudios_anteriores) {
        setEstudiosAnteriores(estudios_anteriores);
    }

    protected <C extends AbstractPersistent<?>> void copyTo(final Object source,
            final Object target,
            final Class<C> clase) {

        final Map<String, Class<?>> camposAlumno = AnnotationsUtil.getInstance()
            .getFields(clase);
        Object valor;
        Object aux;
        Class<?> tipoValor;

        try {

            for (final Object key : camposAlumno.keySet().toArray()) {

                if (!"id".equals(key) && !"codigo".equals(key)) {
                    valor = clase.getMethod(STR_GET.concat(
                                    MiscBase.firstToUpper(key.toString())))
                        .invoke(source);

                    if (valor != null) {
                        tipoValor = clase.getMethod(STR_GET.concat(
                                        MiscBase.firstToUpper(key.toString())))
                            .getReturnType();

                        if (AbstractPersistent.class.isAssignableFrom(
                                    tipoValor)) {
                            aux = clase.getMethod(STR_GET.concat(
                                            MiscBase.firstToUpper(
                                                key.toString())))
                                .invoke(target);

                            if (aux == null) {
                                clase.getMethod(STR_SET.concat(
                                            MiscBase.firstToUpper(
                                                key.toString())), tipoValor)
                                    .invoke(target, valor);
                            } else {
                                copyTo(valor, aux,
                                    tipoValor.asSubclass(
                                        AbstractPersistent.class));
                            }
                        } else {
                            clase.getMethod(STR_SET.concat(
                                        MiscBase.firstToUpper(key.toString())),
                                    tipoValor).invoke(target, valor);
                        }
                    }
                }

            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    public void copyTo(final Alumno alu) {
        copyTo(this, alu, Alumno.class);
    }

    public void setApellido1(final String apellido) {
        this.apellido1 = trim(apellido);

        if (apellido2 == null) {
            setApellidos(apellido1);
        } else if (apellido1 != null) {
            setApellidos(apellido1 + " " + apellido2);
        } else {
            setApellidos(null);
        }

    }

    public void setApellido2(final String apellido) {
        this.apellido2 = trim(apellido);

        if (apellido1 == null) {
            setApellidos(apellido2);
        } else if (apellido2 != null) {
            setApellidos(apellido1 + " " + apellido2);
        } else {
            setApellidos(apellido1);
        }
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }
    
   

    public boolean validar() {
        final boolean dniValido = !UserData.getInstance().isValidarDni()
            || ((getDni() != null) && Misc.nifValidation(getDni()));
        final boolean correoValido = getCorreo() == null || MiscBase.isEmail(getCorreo());
        return (validate().size() == 0) && dniValido && correoValido && !duplicado;
    }
    
    /**
     * Métodos para la internacionalización
     */
    public void setId_card(final String dni){
    	setDni(dni);
    }
    
    public void setFirst_Name(final String name){
    	setNombre(name);
    }
    
    public void setSecond_Name(final String apellidos){
    	setApellidos(apellidos);    	
    }
    
    public void setMail(final String email){
    	setCorreo(email);
    }
    
    public void setSex(final String sex){
    	setTipo_Sexo(sex);
    }
    
    public void setMobile(final String movil){
    	setMovil(movil);
    }
    
    public void setNotes(final String notes){
    	setObservaciones(notes);
    }
    
    public void setIdentifier(final String id){
    	setIdentificacion(id);
    }
    
    public void setBirth_date(final String date){
    	setFecha_Nacimiento(date);
    }
    
    public void setNationality(final String country){
    	setNacionalidad(country);
    } 
}
