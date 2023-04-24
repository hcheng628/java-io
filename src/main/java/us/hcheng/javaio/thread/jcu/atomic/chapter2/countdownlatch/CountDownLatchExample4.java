package us.hcheng.javaio.thread.jcu.atomic.chapter2.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CountDownLatchExample4 {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(6);

		Stream.of(new Event(1), new Event(2), new Event(3)).forEach(e -> {
			List<Table> tables = capture(e);
			TaskGroup taskGroup = new TaskGroup(e, tables.size());
			tables.forEach(t -> {
				Task task = new Task(t, taskGroup);
				service.submit(new SrcRecordCountWorker(task));
				service.submit(new SrcRecordSchemaWorker(task));
			});
		});

		service.shutdown();
	}

	private static List<Table> capture(Event event) {
		List<Table> ret = new ArrayList<>();

		IntStream.range(0, 10).forEach(i -> {
			String srcSchema = "<table name='a'><column name='col' type='varchar2'/></table>";
			long srcRecordCount = new Random().nextInt(100000);
			ret.add(new Table("table-" + i, srcRecordCount, srcSchema));
		});

		return ret;
	}

	static class Event {
		public final int id;
		public Event(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Event{" +
					"id=" + id +
					'}';
		}

	}

	static class Table {
		private final String tableName;
		private final long srcRecordCount;
		private long tarRecordCount;
		private final String srcColSchema;
		private String tarColSchema = "";

		public Table(String tableName, long srcRecordCount, String srcColSchema) {
			this.tableName = tableName;
			this.srcRecordCount = srcRecordCount;
			this.srcColSchema = srcColSchema;
		}

		@Override
		public String toString() {
			return "Table{" +
					"tableName='" + tableName + '\'' +
					", srcRecordCount=" + srcRecordCount +
					", tarRecordCount=" + tarRecordCount +
					", srcColSchema='" + srcColSchema + '\'' +
					", tarColSchema='" + tarColSchema + '\'' +
					'}';
		}
	}

	static class TaskGroup {
		private final Event fEvent;
		private final CountDownLatch fLatch;

		public TaskGroup(Event event, int size) {
			fEvent = event;
			fLatch = new CountDownLatch(size);
		}

		public void complete() {
			fLatch.countDown();
			if (fLatch.getCount() == 0) {
				System.out.println("TaskGroup Complete Event: " + fEvent);
			}
		}
	}

	static class Task {
		private final CountDownLatch fLatch;
		private final Table fTable;
		private final TaskGroup fTaskGroup;

		public Task(Table table, TaskGroup taskGroup) {
			fLatch = new CountDownLatch(2);
			fTable = table;
			fTaskGroup = taskGroup;
		}
		public void complete() {
			fLatch.countDown();
			if (fLatch.getCount() == 0) {
				System.err.println("Task Complete: " + fTable);
				fTaskGroup.complete();
			}
		}
	}

	static class SrcRecordCountWorker implements Runnable {
		private final Task fTask;

		public SrcRecordCountWorker(Task task) {
			this.fTask = task;
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
				fTask.fTable.tarRecordCount = fTask.fTable.srcRecordCount;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			fTask.complete();
			System.out.println("SrcRecordCountWorker: " + fTask.fTable);
		}
	}

	static class SrcRecordSchemaWorker implements Runnable {
		private final Task fTask;

		public SrcRecordSchemaWorker(Task task) {
			this.fTask = task;
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(800);
				fTask.fTable.tarColSchema = fTask.fTable.srcColSchema;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			fTask.complete();
			System.out.println("SrcRecordSchemaWorker: " + fTask.fTable);
		}
	}

}
