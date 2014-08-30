<%@ page import="static com.moonillusions.propertynavigation.PropertyNavigation.to" %>
<%@ page import="static com.moonillusions.propertynavigation.PropertyNavigation.prop" %>
<%@ page import="moonillusions.sulis.controllers.CreateGameCommand" %>

<g:renderErrors as="list" bean="${command.game}"/>

<g:form action="create">

	<g:select
		name="${prop(to(CreateGameCommand.class).game.servingPlayer)}"
		from="${players}"
		optionKey="name"
		optionValue="name" />
		
	<br/>
	or new  one <g:textField name="${prop(to(CreateGameCommand.class).newServingPlayer) }"/>

	<g:select 
		name="${prop(to(CreateGameCommand.class).game.receivingPlayer)}" 
		from="${players}" 
		optionKey="name" 
		optionValue="name" />
		
	<br/>
	or new  one <g:textField name="${prop(to(CreateGameCommand.class).newReceivingPlayer) }"/>
	
	<br/>
	receiving score: 
	<g:select 
		name="${prop(to(CreateGameCommand.class).game.servingPlayerPoints) }"
		from="${[*21..0, *22..25]}" />
	
	<br/>
	serving score: 
	<g:select 
		name="${prop(to(CreateGameCommand.class).game.receivingPlayerPoints) }"
		from="${[*21..0, *22..25]}" />

	<br/>
	date: 
	<g:textField 
		name="${prop(to(CreateGameCommand.class).game.date) }" 
		value="${formatDate(format:'d.M.yyyy',date: command?.game?.date?.toDate())}"/>

	<g:submitButton name="create" />

</g:form>