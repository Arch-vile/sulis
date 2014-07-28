<p>
	I am default. Say <span>hello!</span>
</p>

<g:renderErrors as="list" bean="${game}"/>

<g:form action="create">

	<g:select name="servingPlayer" from="${players}" optionKey="name" optionValue="name" />
	or new  one <g:textField name="newServingPlayer"/>

	<g:select name="receivingPlayer" from="${players}" optionKey="name" optionValue="name" />
	
	<g:select name="receivingPlayerScore" from="${[*21..0, *22..25]}" />

	<g:submitButton name="create" />

</g:form>