
document.getElementById('turnoForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const pacienteDni = document.getElementById('dniPaciente').value;
    const pacienteNombre = document.getElementById('nombrePaciente').value;
    const pacienteApellido = document.getElementById('apellidoPaciente').value;
    const fechaIngreso = document.getElementById('fechaIngresoPaciente').value;
    const calle = document.getElementById('callePaciente').value;
    const numero = document.getElementById('numeroCallePaciente').value;
    const localidad = document.getElementById('localidadPaciente').value;
    const provincia = document.getElementById('provinciaPaciente').value;

    const odontologoMatricula = document.getElementById('matriculaOdontologo').value;
    const odontologoNombre = document.getElementById('nombreOdontologo').value;
    const odontologoApellido = document.getElementById('apellidoOdontologo').value;

    const fechaHora = document.getElementById('fechaHora').value;


    const turnoData = {
        paciente: {
            dni: parseInt(pacienteDni),
            nombre: pacienteNombre,
            apellido: pacienteApellido,
            fechaIngreso: fechaIngreso,
            domicilioEntradaDto: {
                calle: calle,
                numero: parseInt(numero),
                localidad: localidad,
                provincia: provincia
            }
        },
        odontologo: {
            nmatricula: odontologoMatricula,
            nombre: odontologoNombre,
            apellido: odontologoApellido
        },
        fechaHora: fechaHora + ":00"  
    };

    console.log("Datos enviados:", turnoData);  


    fetch('http://localhost:8080/turnos/guardar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turnoData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                throw new Error(errorData.message || 'Error al guardar el turno');
            });
        }
        return response.json();
    })
    .then(data => {
        document.getElementById('message').innerText = "Turno guardado con éxito!";
        console.log('Turno guardado:', data);
    })
    .catch(error => {
        document.getElementById('message').innerText = "Error al guardar el turno: " + error.message;
        console.error('Error:', error);
    });
});

