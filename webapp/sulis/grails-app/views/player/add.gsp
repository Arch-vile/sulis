<g:renderErrors as="list" bean="${command}"/>

<g:form action="add">
	<g:field 
		type="text" 
		name="name" 
		value="${fieldValue(bean: command, field: 'name')}"
		/>
	<g:submitButton name="update" value="Add player" />
</g:form>