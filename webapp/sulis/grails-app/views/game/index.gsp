<p>
	I am default. Say <span>hello!</span>
</p>

<g:renderErrors as="list" bean="${game}"/>

<g:form action="create">

	<g:select name="servingPlayer" from="${players}" optionKey="name" optionValue="name" />
	or new  one <g:textField name="newServingPlayer"/>

	<g:submitButton name="create" />

</g:form>