<g:renderErrors as="list" bean="${command}"/>

<form action="add">
	<input 
		type="text" 
		name="name" 
		value="${fieldValue(bean: command, field: 'name')}"
		/>
	<input type="submit" value="Add player"/>
</form>