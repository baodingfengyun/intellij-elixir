package org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.abstract_code

import com.ericsson.otp.erlang.OtpErlangAtom
import com.ericsson.otp.erlang.OtpErlangObject
import com.ericsson.otp.erlang.OtpErlangTuple
import org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.AbstractCode.ifTag

object Var {
    fun ifToMacroStringDeclaredScope(term: OtpErlangObject?, scope: Scope): MacroStringDeclaredScope? =
            ifTag(term, TAG) { toMacroStringDeclaredScope(it, scope) }

    fun nameToMacroStringDeclaredScope(name: OtpErlangObject, scope: Scope): MacroStringDeclaredScope =
            when (name) {
                is OtpErlangAtom -> nameToMacroStringDeclaredScope(name, scope)
                else -> MacroStringDeclaredScope("unknown_name", Scope.EMPTY)
            }

    fun toMacroStringDeclaredScope(term: OtpErlangTuple, scope: Scope): MacroStringDeclaredScope =
            nameMacroStringDeclaredScope(term, scope)

    private const val IGNORE = "_"
    private const val TAG = "var"

    private fun nameMacroStringDeclaredScope(term: OtpErlangTuple, scope: Scope) =
            toName(term)
                    ?.let{ nameToMacroStringDeclaredScope(it, scope) }
                    ?: MacroStringDeclaredScope("name_missing", Scope.EMPTY)

    private fun nameToMacroStringDeclaredScope(name: OtpErlangAtom, scope: Scope): MacroStringDeclaredScope {
        val varName = name.atomValue().decapitalize()

        return when {
            varName == IGNORE ->
                MacroStringDeclaredScope(varName, Scope(emptySet()))
            scope.varNameSet.contains(varName) -> {
                val pin = if (scope.pinning) {
                    "^"
                } else {
                    ""
                }

                MacroStringDeclaredScope("$pin$varName", Scope.EMPTY)
            }
            else ->
                MacroStringDeclaredScope(varName, Scope(setOf(varName)))
        }
    }

    private fun toName(term: OtpErlangTuple): OtpErlangObject? = term.elementAt(2)
}
