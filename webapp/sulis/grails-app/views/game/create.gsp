<g:renderErrors as="list" bean="${command}"/>

<g:form action="create">


	<g:select
		name="servingPlayerId"
		from="${players}"
		optionKey="id"
		optionValue="name"
		value="${fieldValue(bean: command, field: 'servingPlayerId')}"
		noSelection="['':'-- Choose --']"/>
	<g:link controller="player">Add new player</g:link>
	<br/>
	<g:select 
		name="receivingPlayerId" 
		from="${players}" 
		optionKey="id" 
		optionValue="name"
		value="${fieldValue(bean: command, field: 'receivingPlayerId')}"
		noSelection="['':'-- Choose --']"/>
	<g:link controller="player">Add new player</g:link>
	<br/>
	receiving score: 
	<g:select 
		name="servingPlayerPoints"
		from="${[*21..0, *22..25]}" 
		value="${fieldValue(bean: command, field: 'servingPlayerPoints')}"/>
	<br/>
	serving score: 
	<g:select 
		name="receivingPlayerPoints"
		from="${[*21..0, *22..25]}"
		value="${fieldValue(bean: command, field: 'receivingPlayerPoints')}"/>

	<br/>
	date: 
	<g:textField 
		name="date" 
		value="${formatDate(format:'d.M.yyyy',date: command.date)}"/>

	<g:submitButton name="create" />

</g:form>