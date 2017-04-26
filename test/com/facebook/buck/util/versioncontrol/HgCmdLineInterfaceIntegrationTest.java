import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
  @Rule public ExpectedException exception = ExpectedException.none();
  /** Test constants */

  /**
   * *
   *
   * <p>Test data:
   * <p>The following repo is used in the tests:
   * There are two different variants (both stored inside HG_REPOS_ZIP):
   * <p>hg_repo_two: above, current tip @branch_from_master2, and no local changes. hg_repo_three:
   * above, current tip @branch_from_master3, and with local changes.
   * <p>Additionally hg_repo_with_subdir is a new hg_repo with a directory called subdir
        ImmutableSet.of("A tracked_change", "? local_change"), repoThreeCmdLine.changedFiles("."));
    String commonAncestor =
        repoThreeCmdLine.commonAncestor(BRANCH_FROM_MASTER_THREE_BOOKMARK, MASTER_THREE_BOOKMARK);
    assertTrue(
        repoThreeCmdLine
            .commonAncestorOrAbsent(BRANCH_FROM_MASTER_THREE_BOOKMARK, MASTER_THREE_BOOKMARK)
            .get()
            .startsWith(MASTER_THREE_ID));
    Pair<String, Long> res =
        repoThreeCmdLine
            .commonAncestorAndTSOrAbsent(BRANCH_FROM_MASTER_THREE_BOOKMARK, MASTER_THREE_BOOKMARK)
            .get();
            BRANCH_FROM_MASTER_THREE_BOOKMARK, "absent_bookmark"),
        ImmutableSet.of("A tracked_change", "? local_change"), repoThreeCmdLine.changedFiles("."));
            "A tracked_change", "A change3", "A change3-2", "? local_change"));
    String commonAncestor =
        repoThreeCmdLine.commonAncestor(BRANCH_FROM_MASTER_THREE_ID, MASTER_THREE_ID);
    String commonAncestor =
        repoTwoCmdLine.commonAncestor(BRANCH_FROM_MASTER_TWO_ID, MASTER_THREE_ID);
    ImmutableList<String> expectedValue =
        ImmutableList.of(
            "# HG changeset patch",
            "# User Joe Blogs <joe.blogs@fb.com>",
            "# Date 1440589545 -3600",
            "#      Wed Aug 26 12:45:45 2015 +0100",
            "# Node ID 2911b3cab6b24374a3649ebb96b0e53324e9c02e",
            "# Parent  b1fd7e5896af8aa30e3e797ef1445605eec6d055",
            "diverge from master_2",
            "",
            "diff --git a/change2 b/change2",
            "new file mode 100644",
            "");
    assertThat(MASTER_THREE_TS, is(equalTo(repoThreeCmdLine.timestampSeconds(MASTER_THREE_ID))));
    ImmutableMap<String, String> bookmarks =
        ImmutableMap.of("branch_from_master2", "2911b3cab6b24374a3649ebb96b0e53324e9c02e");
    assertEquals(bookmarks, repoThreeCmdLine.bookmarksRevisionsId(bookmarks.keySet()));
    bookmarks = ImmutableMap.of("branch_from_master3", "dee6702e3d5e38a86b27b159a8a0a34205e2065d");
    assertEquals(bookmarks, repoThreeCmdLine.bookmarksRevisionsId(bookmarks.keySet()));
      throws VersionControlCommandFailedException, InterruptedException, IOException {
    List<String> lines =
        Files.readAllLines(
            Paths.get(path), Charset.forName(System.getProperty("file.encoding", "UTF-8")));
    List<String> expected =
        ImmutableList.of(
            "change2\0b80de5d138758541c5f05265ad144ab9fa86d1db",
            "file1\0b80de5d138758541c5f05265ad144ab9fa86d1db",
            "file2\0b80de5d138758541c5f05265ad144ab9fa86d1db");
    HgCmdLineInterface hgCmdLineInterface =
        (HgCmdLineInterface) makeCmdLine(localReposPath.resolve(REPO_TWO_DIR));
    List<String> lines =
        Files.readAllLines(
            Paths.get(path), Charset.forName(System.getProperty("file.encoding", "UTF-8")));
    List<String> expected =
        ImmutableList.of(
            "change2\u0000b80de5d138758541c5f05265ad144ab9fa86d1db",
            "file1\u0000b80de5d138758541c5f05265ad144ab9fa86d1db",
            "file2\u0000b80de5d138758541c5f05265ad144ab9fa86d1db",
            "file1\u00000000000000000000000000000000000000000000d");
      throws VersionControlCommandFailedException, InterruptedException, IOException {
    HgCmdLineInterface hgCmdLineInterface =
        (HgCmdLineInterface) makeCmdLine(reposPath.resolve(REPO_WITH_SUB_DIR + "/subdir"));
    HgCmdLineInterface hgCmdLineInterface =
        new HgCmdLineInterface(
            new TestProcessExecutorFactory(),
            reposPath,
            new VersionControlBuckConfig(FakeBuckConfig.builder().build()).getHgCmd(),
            ImmutableMap.of());
        hgRepoZipCopyPath, reposPath, Unzip.ExistingFileMode.OVERWRITE_AND_CLEAN_DIRECTORIES);