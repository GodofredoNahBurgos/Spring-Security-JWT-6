SELECT usuario.username, role.name 
from usuario 
JOIN usuarios_roles ON usuario.id_usuario = usuarios_roles.usuario_id
JOIN role ON usuarios_roles.role_id = role.id_role