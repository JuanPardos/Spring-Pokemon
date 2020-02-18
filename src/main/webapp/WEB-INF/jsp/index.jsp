<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Página Principal</title>
	<link href="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/cosmo/bootstrap.min.css" rel="stylesheet">
</head>
<body style="margin-left:10px" background="https://wallpaperaccess.com/full/509313.jpg">
	<audio controls loop style="display: block; margin-left: auto; margin-right: auto">
		<source src="http://23.237.126.42/ost/pokemon-ruby-sapphire-music-super-complete/ktnxrati/1-09%20Battle%21%20Wild%20Pok%C3%A9mon.mp3" type="audio/mpeg">
				  
				  Your browser does not support the audio tag.
	</audio>
	<b>
		<form:form action="insert" method="post" modelAttribute="trainer">
			<span>NUEVO ENTRENADOR:</span>
			<form:input type="text" path="name" />
			<input type="submit" value="Guardar entrenador" />
		</form:form>
		<br />
		<br /> El nombre del entrenador es
		<span style="color: orange"><c:out value="${trainer.name}" /></span>
		<br />
		<br />
		<br />
	
	
		<form:form action="insert" method="post" modelAttribute="trainer">
			<span>NUEVO POKEMON: &nbsp;</span>
			<span style="color:lime">Nombre &nbsp;</span>
			<form:input type="text" path="pokemon.name" /> &nbsp;
			<span style="color:lime">Nivel &nbsp;</span>
			<form:input type="text" path="pokemon.level" /> &nbsp;
			<span style="color:lime">Tipo &nbsp;</span>
<%-- 			<form:input type="text" path="pokemon.type"/> &nbsp; --%> 
			<form:select path="pokemon.type"> &nbsp;
				<form:option value="Fuego">Fuego</form:option>
				<form:option value="Agua">Agua</form:option>
				<form:option value="Planta">Planta</form:option>
			</form:select>
			<input type="submit" value="Guardar pokemon" />
		</form:form>
		<br/>
		<table border="1" style="background-color: aqua">
			<thead>
				<tr>
					<td>#</td>
					<td>Nombre</td>
					<td>Tipo</td>
					<td>Estado</td>
					<td>Nivel</td>
					<td>HP</td>
					<td>Ataque</td>
				</tr>
			</thead>
			<tbody>
				<c:set var = "count2" scope = "page" value = "1"/>
				<c:forEach var="item" items="${trainer.team.pokemons}">
					<tr>
						<td style="text-align:center"><c:out value="${count2}" /></td>
						<td style="text-align:center"><c:out value="${item.name}" /></td>
						<td style="text-align:center"><c:out value="${item.type}" /></td>
						<td style="text-align:center"><c:out value="${item.status}" /></td>
						<td style="text-align:center"><c:out value="${item.level}" /></td>
						<td style="text-align:center"><c:out value="${item.HP}" /></td>
						<td style="text-align:center"><c:out value="${item.attack}" /></td>
					</tr>
					<c:set var="count2" value="${count2 + 1}" scope="page"/>
				</c:forEach>
			</tbody>
		</table>
		<br />
		
		<table>
			<tr>
				<td>
					<form:form action="switchPokemon" method="post" modelAttribute="trainer">
						<form:select path="aux">
							<c:set var = "count" scope = "page" value = "0"/>
							<c:forEach var="item" items="${trainer.team.pokemons}">
								<form:option value="${count}"><c:out value="${item.name} Nv. ${item.level}"/></form:option>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</form:select>
						<input type="submit" value="Cambiar pokemon activo" />
					</form:form>
				</td>
			</tr>
			<tr>
				<td>
					<form:form action="releasePokemon" method="post" modelAttribute="trainer">
						<form:select path="aux2">
							<c:set var = "count" scope = "page" value = "0"/>
							<c:forEach var="item" items="${trainer.team.pokemons}">
								<form:option value="${count}"><c:out value="${item.name} Nv. ${item.level}"/></form:option>
								<c:set var="count" value="${count + 1}" scope="page"/>
							</c:forEach>
						</form:select>
						<input type="submit" value="✖️ Liberar Pokemon ✖️" style="background-color:red" />
					</form:form>
				</td>
			</tr>
		</table>
		
		<br/><br/>
		
		<table>
			<tr>
				<td>
					<form:form action="createEnemy" method="post" modelAttribute="trainer" title="Busca un nuevo pokemon salvaje">
						<input type="submit" value="🌿️ Entrar en la hierba alta 🌿️"/>
					</form:form>
				</td>
				<td>
					<form:form action="heal" method="post" modelAttribute="trainer" title="Usar una superpoción +50 HP">
						<input type="submit" value="💊️ Usar superpoción 💊️"/>
					</form:form>
				</td>
				<td>
					<form:form action="combat" method="post" modelAttribute="trainer" title="Ataque del tipo del Pokemon">
						<input type="submit" value="⚔️ Ataque tipo <c:out value="${trainer.primary.type}"/> ⚔️"/>
					</form:form>
				</td>
				<td>
					<form:form action="sleep" method="post" modelAttribute="trainer" title="Atacar con Somnífero, 60% prob. Facilita la captura.">
						<input type="submit" value="💤️ Usar somnífero 💤️"/>
					</form:form>
				</td>
			</tr>
			<tr>
				<td>
					<form:form action="falseAttack" method="post" modelAttribute="trainer" title="Ataca con FalsoTortazo, ataque menos potente que no debilita al enemigo">
						<input type="submit" value="🗡️ Atacar (FalsoTortazo) 🗡️"/>
					</form:form>
				</td>
				<td>
					<c:if test="${trainer.wildPokemon.HP >= trainer.wildPokemon.maxHP*0.25}">
				 		<form:form action="capture" method="post" modelAttribute="trainer" title="Lanza la pokeball segun el profile en uso">
							<input type="submit" value="⭕️ Lanzar pokeball ⭕️" disabled="disabled"/>
						</form:form>
				 	</c:if>
				 	<c:if test="${trainer.wildPokemon.HP <= trainer.wildPokemon.maxHP*0.25}">
				 		<form:form action="capture" method="post" modelAttribute="trainer" title="Lanza la pokeball segun el profile en uso">
							<input type="submit" value="⭕️ Lanzar pokeball ⭕️"/>
						</form:form>
				 	</c:if>
			 	</td>
				<td>
					<button onclick="alert('OFERTON de Lanzamiento: 1xMasterBall=4.99$  2xMasterBall=9.99$  3xMasterBall=14.99$  ...')">💰️ Comprar MasterBall 💰️</button>
				</td>
			</tr>
		</table>
		
		
		<br />
	 	<a>Pokemon Activo</a><br/><i><c:out value="${trainer.primary.name}"/></i> &nbsp; Tipo:
	 	<c:if test="${trainer.primary.type == 'Planta'}">
	 		<a style="color: #97ff00"><c:out value="${trainer.primary.type}"/></a>
	 	</c:if>
	 	<c:if test="${trainer.primary.type == 'Fuego'}">
	 		<a style="color: red"><c:out value="${trainer.primary.type}"/></a>
	 	</c:if>
	 	<c:if test="${trainer.primary.type == 'Agua'}">
	 		<a style="color: aqua"><c:out value="${trainer.primary.type}"/></a>
	 	</c:if>&#8212;
		<c:out value="${trainer.primary.status}"/>&#8212; Nivel:
		<c:out value="${trainer.primary.level}"/> Vida: &nbsp;
		<c:out value="${trainer.primary.HP}"/> &#47;
		<c:out value="${trainer.primary.maxHP}"/> Ataque: &nbsp;
		<c:out value="${trainer.primary.attack}"/>
		<br/>
		<a>Pokemon Enemigo</a><br/><i><c:out value="${trainer.wildPokemon.name}"/></i> &nbsp; Tipo: 
		<c:if test="${trainer.wildPokemon.type == 'Agua'}">
	 		<a style="color: aqua"><c:out value="${trainer.wildPokemon.type}"/></a>
	 	</c:if>
	 	<c:if test="${trainer.wildPokemon.type == 'Fuego'}">
	 		<a style="color: red"><c:out value="${trainer.wildPokemon.type}"/></a>
	 	</c:if>
	 	<c:if test="${trainer.wildPokemon.type == 'Planta'}">
	 		<a style="color: #97ff00"><c:out value="${trainer.wildPokemon.type}"/></a>
	 	</c:if>&#8212;
		Nivel: <c:out value="${trainer.wildPokemon.level}"/> Vida: &nbsp;
		<c:out value="${trainer.wildPokemon.HP}"/> &#47;
		<c:out value="${trainer.wildPokemon.maxHP}"/> Ataque: &nbsp;
		<c:out value="${trainer.wildPokemon.attack}"/> 
		<br/>Indice de captura: <c:out value="${trainer.wildPokemon.captureRate}"/>&nbsp;
		<c:if test="${trainer.wildPokemon.status == 'Durmiendo'}">
	 		<a style="color: gold">*DURMIENDO*</a>
	 	</c:if>
		<br/>
		<h2>INFO:</h2>
		<c:out value="${trainer.feedback}"/>
	</b>
	<img src="https://preview.redd.it/rskneik2r4h41.jpg?width=640&height=804&crop=smart&auto=webp&s=8ad35f9722888c349c6d011389aa8fbcf5f051c5" width="445px" height="560px" style="position:absolute; right: 25px; top:25px; z-index: -1">
	<!--<img src="https://i.kym-cdn.com/entries/icons/mobile/000/028/021/work.jpg" width="380px" height="240px" style="position:absolute; left:25px; bottom: 25px; z-index: -1"> -->
</body>
</html>