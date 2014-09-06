<%@ page import="static com.moonillusions.propertynavigation.PropertyNavigation.to" %>
<%@ page import="static com.moonillusions.propertynavigation.PropertyNavigation.of" %>
<%@ page import="static com.moonillusions.propertynavigation.PropertyNavigation.prop" %>
<%@ page import="moonillusions.sulis.controllers.CreateGameCommand" %>

<g:renderErrors as="list" bean="${command?.game}"/>

<g:form action="create">


	<g:select
		name="game.servingPlayer"
		from="${players}"
		optionKey="id"
		optionValue="name"
		value="${fieldValue(bean: createGameCommand, field: 'game.servingPlayer.id')}"/>
	<br/>
	or new  one:  
		
	<g:textField 
		name="newServingPlayer"
		value="${fieldValue(bean: createGameCommand, field: 'newServingPlayer')}"/>

	<g:select 
		name="game.receivingPlayer" 
		from="${players}" 
		optionKey="id" 
		optionValue="name"
		value="${fieldValue(bean: createGameCommand, field: 'game.receivingPlayer.id')}"/>
		
	<br/>
	or new  one
	<g:textField 
		name="${prop(of(CreateGameCommand.class).newReceivingPlayer) }"
		value="${fieldValue(bean: createGameCommand, field: 'newReceivingPlayer')}"/>
	
	<br/>
	receiving score: 
	<g:select 
		name="game.servingPlayerPoints"
		from="${[*21..0, *22..25]}" 
		value="${fieldValue(bean: createGameCommand, field: 'game.servingPlayerPoints')}"/>
	<br/>
	serving score: 
	<g:select 
		name="game.receivingPlayerPoints"
		from="${[*21..0, *22..25]}"
		value="${fieldValue(bean: createGameCommand, field: 'game.receivingPlayerPoints')}"/>

	<br/>
	date: 
	<g:textField 
		name="${prop(of(CreateGameCommand.class).game.date) }" 
		value="${formatDate(format:'d.M.yyyy',date: createGameCommand.game.date.toDate())}"/>

	<g:submitButton name="create" />

</g:form>