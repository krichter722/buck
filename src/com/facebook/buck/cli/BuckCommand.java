/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.cli;

import com.facebook.buck.util.HumanReadableException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommands;

public class BuckCommand extends AbstractContainerCommand {

  @Argument(handler = AdditionalOptionsSubCommandHandler.class, metaVar = "subcommand")
  @SubCommands({
    @SubCommand(name = "audit", impl = AuditCommand.class),
    @SubCommand(name = "build", impl = BuildCommand.class),
    @SubCommand(name = "cache", impl = CacheCommand.class),
    @SubCommand(name = "clean", impl = CleanCommand.class),
    @SubCommand(name = "distbuild", impl = DistBuildCommand.class),
    @SubCommand(name = "doctor", impl = DoctorCommand.class),
    @SubCommand(name = "fetch", impl = FetchCommand.class),
    @SubCommand(name = "help", impl = HelpCommand.class),
    @SubCommand(name = "install", impl = InstallCommand.class),
    @SubCommand(name = "kill", impl = KillCommand.class),
    @SubCommand(name = "machoutils", impl = MachOUtilsCommand.class),
    @SubCommand(name = "project", impl = ProjectCommand.class),
    @SubCommand(name = "publish", impl = PublishCommand.class),
    @SubCommand(name = "query", impl = QueryCommand.class),
    @SubCommand(name = "rage", impl = DoctorCommand.class),
    @SubCommand(name = "root", impl = RootCommand.class),
    @SubCommand(name = "run", impl = RunCommand.class),
    @SubCommand(name = "server", impl = ServerCommand.class),
    @SubCommand(name = "suggest", impl = SuggestCommand.class),
    @SubCommand(name = "targets", impl = TargetsCommand.class),
    @SubCommand(name = "test", impl = TestCommand.class),
    @SubCommand(name = "uninstall", impl = UninstallCommand.class),
    @SubCommand(name = "verify-caches", impl = VerifyCachesCommand.class),
  })
  @Nullable
  Command subcommand;

  // --version is handled in python, but leave it here for --help
  @Option(
    name = "--version",
    aliases = {"-V"},
    usage = "Show version number."
  )
  private boolean version;

  @Override
  public OptionalInt runHelp(PrintStream stream) {
    if (subcommand != null && subcommand instanceof HelpCommand) {
      return OptionalInt.of(((HelpCommand) subcommand).run(stream));
    }
    return super.runHelp(stream);
  }

  @Override
  public int run(CommandRunnerParams params) throws IOException, InterruptedException {
    return super.run(params);
  }

  @Override
  public boolean isReadOnly() {
    return subcommand == null || subcommand.isReadOnly();
  }

  @Override
  public String getShortDescription() {
    return "Buck build tool";
  }

  @Override
  protected String getContainerCommandPrefix() {
    return "buck";
  }

  @Override
  public Optional<Command> getSubcommand() {
    return Optional.ofNullable(subcommand);
  }

  /**
   * @return String'fied version of the SubCommand or "no_sub_command" if the SubCommand is null.
   */
  public String getSubCommandNameForLogging() {
    if (subcommand == null) {
      return "no_sub_command";
    } else {
      return subcommand.getClass().getSimpleName().toLowerCase();
    }
  }

  @Override
  public boolean isSourceControlStatsGatheringEnabled() {
    return false;
  }

  /**
   * @return The name that was used to invoke the subcommand, as declared in the annotations above
   */
  String getDeclaredSubCommandName() {
    if (subcommand == null) {
      return "no_sub_command";
    } else {
      final Class<? extends Command> subcommandClass = subcommand.getClass();
      try {
        final SubCommands subCommands =
            this.getClass()
                .getDeclaredField(getSubcommandsFieldName())
                .getAnnotation(SubCommands.class);
        for (SubCommand c : subCommands.value()) {
          if (c.impl().equals(subcommandClass)) {
            return c.name();
          }
        }
        return "unknown_sub_command";
      } catch (NoSuchFieldException e) {
        throw new HumanReadableException(e.getMessage());
      }
    }
  }
}
